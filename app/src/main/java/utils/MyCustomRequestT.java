package utils;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;




/**
 * Created by 暗示语 on 2016/11/7.
 */

public class MyCustomRequestT<T> extends Request<T> {
    private Response.Listener <T> listener;
    private Class<T> clazz;
    /**
     * 通过对StringRequest的源码的分析我们可以看到的是
     * parseNetworkResponse和deliverResponse
     * 对我们来说就好像Asynctasks中的doinbackgroud和onprostExecute的方法 因此
     * 如果我们自定义请求的时候耗时的操做我们可以在parseNetworkResponse中进行
     * 而执行的最后的结果我们可以在deliverResponse中进行
     *
     *
     * 具体的写法我们可以参考StringRequest的请求的类的源码 构造函数的书写
     * */

    public MyCustomRequestT(String url, Response.Listener<T> listener , Response.ErrorListener errorlistener,Class<T> clazz) {
        this(Method.GET,url,listener,errorlistener,clazz);

    }
    public MyCustomRequestT(int method, String url, Response.Listener <T> listener , Response.ErrorListener errorlistener,Class<T> clazz) {
        super(method,url,errorlistener);
        this.listener=listener;
        this.clazz=clazz;
    }




    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        //解析需要GSON需要下载架包
        //这里我们可以直接使用StringRequest的请求得到的数据然后我们根据字符串进行解析
        //得到json的字符转
        String json;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            json = new String(response.data);
        }
        //解析JSOn的数据
        Gson gson=new Gson();
        T bzBean=gson.fromJson(json,clazz);

        return Response.success(bzBean, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        if (listener != null) {
            listener.onResponse(response);
        }
    }



}
