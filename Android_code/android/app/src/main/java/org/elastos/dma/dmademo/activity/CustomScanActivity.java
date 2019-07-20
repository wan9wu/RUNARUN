package org.elastos.dma.dmademo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.elastos.dma.dmademo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by xianxian on 2017/4/12.
 */

public class CustomScanActivity extends Activity implements DecoratedBarcodeView.TorchListener,View.OnClickListener { // 实现相关接口
    // 添加一个按钮用来控制闪光灯，同时添加两个按钮表示其他功能，先用Toast表示
    Button swichLight;
    DecoratedBarcodeView mDBV;
    private CaptureManager captureManager;
    private boolean isLightOn = false;
    private final String mPageName = this.getClass().getName();
    final static int SENARIO_NORMAL = 0;
    private int mark = 0;

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customscan);

        mDBV= (DecoratedBarcodeView) findViewById(R.id.dbv_custom);

        mDBV.setTorchListener(this);

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();


        TextView topbar_title=(TextView)findViewById(R.id.topbar_title);
        LinearLayout topbar_back=(LinearLayout) findViewById(R.id.topbar_back);
        topbar_back.setOnClickListener(this);
        topbar_title.setText(getString(R.string.wallet_scan));

        prepareData();
    }


    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.topbar_back:
              finish();
                break;
        }
    }
    private void prepareData() {

        String rootPath = Environment.getExternalStorageDirectory().getPath()+File.separator+"data"+File.separator;
        CopyAssets2SDcard(CustomScanActivity.this, "", rootPath, true);

    }


    // tool function
    private static boolean CopyAssets2SDcard(Context context, String assetDir, String dir, boolean needRefresh) {
        String[] files;

        try {
            files = context.getResources().getAssets().list(assetDir);
            File mWorkingPath = new File(dir);

            if (!mWorkingPath.exists()) {
                if (!mWorkingPath.mkdirs()) {

                }
            }
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                // we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        CopyAssets2SDcard(context, fileName, dir + fileName + "/", needRefresh);
                    } else {
                        CopyAssets2SDcard(context, assetDir + "/" + fileName, dir + fileName + "/", needRefresh);
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists()) {
                    if (needRefresh) {
                        outFile.delete();
                    } else {
                        return true;
                    }
                }
                InputStream in = null;
                if (0 != assetDir.length()) {
                    in = context.getAssets().open(assetDir + "/" + fileName);
                } else {
                    in = context.getAssets().open(fileName);
                }
                OutputStream out = new FileOutputStream(outFile);

                // Transfer bytes from in to out
                byte[] buf = new byte[2048];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
            }
        } catch (IOException e1) {
            return false;
        }
        return true;
    }
}