package utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mi.ten.R;

/**
 * Created by 暗示语 on 2016/11/16.
 */

public class MyDialogCustom {

/**
 * 自定义加载的动画
 * */
    public  Dialog getDialog(Context context)
    {
        LayoutInflater inflater=LayoutInflater.from(context);
       View view= inflater.inflate(R.layout.mycustomdialog,null);
        LinearLayout layout= (LinearLayout) view.findViewById(R.id.dialog_view);
        ImageView iv= (ImageView) view.findViewById(R.id.load);
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.loading);
        iv.startAnimation(animation);
        Dialog loadDialog=new Dialog(context,R.style.lo);
        loadDialog.setCancelable(false);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadDialog.setContentView(layout,params);
        return loadDialog;
    }
}
