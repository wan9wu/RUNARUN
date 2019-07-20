package org.elastos.dma.dmademo.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.config.SystemConfig;
import org.elastos.dma.dmademo.net.HttpEngine;
import org.elastos.dma.dmademo.tool.LQRPhotoSelectUtils;
import org.elastos.dma.dmademo.tool.NodeClient;
import org.elastos.dma.dmademo.tool.Waiter;
import org.elastos.dma.service.merchant.ETHMerchantService;
import org.elastos.dma.service.merchant.impl.ETHMerchantServiceImpl;
import org.elastos.dma.service.storage.ipfs.IpfsService;
import org.elastos.dma.utility.util.JsonResult;
import org.elastos.dma.utility.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    ImageView img_upload;
    Button btn_create;
    EditText edt_name,edt_production,edt_condition,edt_qm_price,edt_qm_num;
    CheckBox check_qm;
    StartActivity mainActivity;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    String avatarUrl="";
    private final int SAVE_IMG_SUCCESS=1;
    private final int START_SUCCESS=2;
    private final int START_IPROGRESS=3;
    ETHMerchantService ethMerchantService;
    Boolean isPost=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mainActivity =StartActivity.this;
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ethMerchantService=NodeClient.getEthMerchantService();
        mToolbar.setNavigationIcon(R.drawable.round_arrow_back_24);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_upload=findViewById(R.id.img_upload);
        btn_create=findViewById(R.id.btn_create);
        edt_name=findViewById(R.id.edt_name);
        edt_production=findViewById(R.id.edt_production);
        edt_condition=findViewById(R.id.edt_condition);
        edt_qm_price=findViewById(R.id.edt_qm_price);
        edt_qm_num=findViewById(R.id.edt_qm_num);
        check_qm=findViewById(R.id.check_qm);
        img_upload.setOnClickListener(this);
        btn_create.setOnClickListener(this);

        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                Glide.with(mainActivity).load(outputUri).into(img_upload);

                File file1=new File(outputUri.getPath());
                uploadImg(file1);


            }
        }, 1,1,200,200);//true裁剪，false不裁剪

    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what>0){
                switch (msg.what){
                    // 图片已修改
                    case SAVE_IMG_SUCCESS:

                        break;
                    case START_IPROGRESS:
                        Waiter.initProgressDialog(mainActivity,"活动发布中...");
                        break;
                    case START_SUCCESS:{
                        Intent intent = new Intent();
                        intent.setClass(mainActivity, StartActivity.class);
                        startActivity(intent);
                        Waiter.alertErrorMessage("创建成功",mainActivity);
                        //跳转到成功页
                    }
                        break;

                    default:
                        break;
                }
            }
        }
    };

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }
    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle("权限申请");
        //设置正文
        builder.setMessage("在设置-应用-权限 中开启相机、存储权限，才能正常使用拍照或图片选择功能");

        //添加确定按钮点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + mainActivity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }
    @Override
    protected void onResume() {
        super.onResume();
        mToolbar.setTitleTextColor(0xFF002BB5);
        mToolbar.setTitle("活动发布");
    }

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, StartActivity.class);
        context.startActivity(intent);
    }
    private void deploy(){
        String name=edt_name.getText().toString();
        String production=edt_production.getText().toString();
        String condition=edt_condition.getText().toString();
        String qm_price=edt_qm_price.getText().toString();
        String qm_num=edt_qm_num.getText().toString();
//        if(StringUtils.isEmpty(avatarUrl)){
//            Waiter.alertErrorMessage("请设置图片",mainActivity);
//            return;
//        }
        if(!Waiter.CheckEditText(edt_name)||!Waiter.CheckEditText(edt_production)||!Waiter.CheckEditText(edt_qm_price)||!Waiter.CheckEditText(edt_qm_num)){
            return;
        }
        if(isPost){
            return;
        }
        isPost=true;
        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void... params) {
                Map<String, Object> map=new HashMap<String, Object>();
                JsonResult<ETHMerchantServiceImpl.DeployResult> jsonResult=ethMerchantService.deploy(SystemConfig.privatekey,NodeClient.gasPrice,NodeClient.gasLimit,name,production,"");

                isPost=false;
                if(jsonResult!=null&&jsonResult.getSuccess()&&jsonResult.getData()!=null){
                   String assetAddress=jsonResult.getData().getAssetAddress();
                   String platformAddress=jsonResult.getData().getPlatformAddress();

                   System.out.println("发布合约assetAddress===="+assetAddress);
                   System.out.println("发布合约platformAddress===="+platformAddress);
                   if(assetAddress!=null&&platformAddress!=null){
                       //提交本地网络请求
//
                       deployStorage(name,production,condition,qm_price,qm_num,"0","",assetAddress,platformAddress);
                   }
               }

                return 1;
            }
            protected void onPostExecute(Integer result) {
                if(result!=null&&result.intValue()==1){
                 //   handler.sendEmptyMessage(START_SUCCESS);
                }
            };
        }.execute();
    }

    private void deployStorage(String name, String production,String condition, String price, String count,  String remark, String hashaddress,String trestaddress,String contractaddress){


        /*票務狀態： 默认-0     已发布-1   已上架-2 */

        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void... params) {
                Map<String, String> map=new HashMap<String, String>();
                map.put("id",UUID.randomUUID().toString());
                map.put("name",name);
                map.put("production",production);
                map.put("condition",condition);
                map.put("price",price);
                map.put("count",count);
                map.put("remark",remark);
                map.put("hashaddress",hashaddress);
                map.put("trestaddress",trestaddress);
                map.put("contractaddress",contractaddress);
                Response response = HttpEngine.sendGetRequest("/maven_demo/addTicket.do", map);
                try {
                    if (response == null) {
                        return null;
                    } else {
                        String responseStr = response.body().string();

                        Log.i("Result", responseStr);
                        return 1;
                    }
                } catch (IOException e) {

                }
                return 1;
            }
            protected void onPostExecute(Integer result) {
                if(result!=null&&result.intValue()==1){
                    handler.sendEmptyMessage(START_SUCCESS);
                }
            };
        }.execute();
    }

    private void showSettingFaceDialog() {
        String[] items = new String[] { getString(R.string.photo_local), getString(R.string.photo_camera) };
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.photo_soruce))
                .setCancelable(true)
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:// Local Image
                                // 3、调用从图库选取图片方法
                                PermissionGen.needPermission(mainActivity,
                                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                );
                                break;
                            case 1:// Take Picture
                                // 3、调用拍照方法
                                PermissionGen.with(mainActivity)
                                        .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA
                                        ).request();
                                break;
                        }
                    }
                })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }

    protected void uploadImg(File file){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                Map<String, Object> map=new HashMap<String, Object>();
                IpfsService ipfsService=NodeClient.getIpfsServive();
                String hash = null;
                try {
                    hash = ipfsService.putFile(file);
                    System.out.println("上传图片hash===="+hash);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(StringUtils.isNotEmpty(hash)){
                    avatarUrl=ipfsService.getHttpUrlByHash(hash);
                    System.out.println("图片地址===="+avatarUrl);
                }

                return avatarUrl;
            }
            protected void onPostExecute(String result) {

                if(result!=null&&result.length()>0){
                    handler.sendEmptyMessage(SAVE_IMG_SUCCESS);
                }
            };
        }.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_upload:
                showSettingFaceDialog();
                break;
            case R.id.btn_create:
                deploy();
                break;
        }
    }
}
