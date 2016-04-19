package com.blazefire.perry.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blazefire.perry.adapter.base.AdapterBase;
import com.blazefire.perry.entity.ProxyInfo;
import com.blazefire.perry.proxy.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/13 16:01
 */
public class AdapterProxyInfo extends AdapterBase<ProxyInfo> {
    private LayoutInflater mInflater;
    private BitmapFactory.Options opt;
    public AdapterProxyInfo(Context context){
        this.mInflater = LayoutInflater.from(context);
        if (opt ==null){
            this.opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
        }
    }
    public final class ViewHolder{
     public ImageView CountryIcon;
     public TextView CountryName;
     public TextView IPAddrandPort;
     public TextView ConnectionSpeed;
    }
    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }
    @Override
    protected View getExView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_ip_addr_info, null);
            holder.CountryIcon = (ImageView) convertView.findViewById(R.id.CountryIcon);
            holder.CountryName = (TextView) convertView.findViewById(R.id.CountryName);
            holder.IPAddrandPort = (TextView) convertView.findViewById(R.id.IPAddrandPort);
            holder.ConnectionSpeed = (TextView) convertView.findViewById(R.id.ConnectionSpeed);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        ProxyInfo proxy = (ProxyInfo)getItem(position);
        if (proxy != null){
            //ProxyInfo proxy = getList().get(position);
            holder.CountryName.setText(proxy.getmCountry());
            holder.IPAddrandPort.setText(proxy.getmIpAddr()+":"+proxy.getmPort());
            Drawable d=convertView.getResources().getDrawable(R.mipmap.am);
            holder.CountryIcon.setImageDrawable(d);

        }

        return convertView;
    }

    @Override
    protected void onReachBottom() {

    }
}
