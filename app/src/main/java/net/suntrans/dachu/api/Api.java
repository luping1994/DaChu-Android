package net.suntrans.dachu.api;

import net.suntrans.dachu.bean.AddSceneChannelResult;
import net.suntrans.dachu.bean.Ameter3Entity;
import net.suntrans.dachu.bean.AmmeterInfos;
import net.suntrans.dachu.bean.ChannelEditorInfo;
import net.suntrans.dachu.bean.DeviceDetailResult;
import net.suntrans.dachu.bean.FreshChannelEntity;
import net.suntrans.dachu.bean.HisEntity;
import net.suntrans.dachu.bean.RespondBody;
import net.suntrans.dachu.bean.SampleResult;
import net.suntrans.dachu.bean.Ammeter3Eneity;
import net.suntrans.dachu.bean.AreaDetailEntity;
import net.suntrans.dachu.bean.AreaEntity;
import net.suntrans.dachu.bean.ChangedPasswordEntity;
import net.suntrans.dachu.bean.ControlEntity;
import net.suntrans.dachu.bean.DeviceEntity;
import net.suntrans.dachu.bean.DeviceInfoResult;
import net.suntrans.dachu.bean.EnergyEntity;
import net.suntrans.dachu.bean.EnvDetailEntity;
import net.suntrans.dachu.bean.LoginResult;
import net.suntrans.dachu.bean.SceneChannelResult;
import net.suntrans.dachu.bean.SceneEdit;
import net.suntrans.dachu.bean.SceneEntity;
import net.suntrans.dachu.bean.SceneTimeResult;
import net.suntrans.dachu.bean.SensusEntity;
import net.suntrans.dachu.bean.UpLoadImageMessage;
import net.suntrans.dachu.bean.UserInfo;
import net.suntrans.dachu.bean.YichangEntity;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Looney on 2017/1/4.
 */

public interface Api {

    /**
     * 登录api
     *
     * @param grant_type    默认填password
     * @param client_id     默认填6
     * @param client_secret 默认填test
     * @param username      账号
     * @param password      密码
     * @return
     */
    @FormUrlEncoded
    @POST("oauth/token")
    Observable<LoginResult> login(@Field("grant_type") String grant_type,
                                  @Field("client_id") String client_id,
                                  @Field("client_secret") String client_secret,
                                  @Field("username") String username,
                                  @Field("password") String password);

    @POST("home/scene")
    Observable<SceneEntity> getHomeScene();

    @FormUrlEncoded
    @POST("scene/show")
    Observable<SceneChannelResult> getSceneChannel(@Field("id") String id);

    @FormUrlEncoded
    @POST("switch/channel")
    Observable<ControlEntity> switchChannel(@Field("id") String id,
                                            @Field("datapoint") String datapoint,
                                            @Field("din") String din,
                                            @Field("cmd") String cmd);


    @POST("home/light")
    Observable<DeviceEntity> getAllDevice();

    @FormUrlEncoded
    @POST("switch/scene")
    Observable<ControlEntity> switchScene(@Field("id") String id);


    @POST("house/index")
    Observable<AreaEntity> getHomeHouse();

    @FormUrlEncoded
    @POST("house/area")
    Observable<AreaDetailEntity> getRoomChannel(@Field("id") String id);

    @FormUrlEncoded
    @POST("energy/ammeter3")
    Observable<Ammeter3Eneity> getAmmeter3Detail(@Field("sno") String sno);

    @POST("energy/index")
    Observable<EnergyEntity> getEnergyIndex();

    @POST("home/sensus")
    Observable<SensusEntity> getHomeSceneNew();


    @POST("device/index")
    Observable<DeviceInfoResult> getDevicesInfo();

    @FormUrlEncoded
    @POST("sensus/show")
    Observable<EnvDetailEntity> getEnvDetail(@Field("id") String id);


    @POST("user/info")
    Observable<UserInfo> getUserInfo();


    @FormUrlEncoded
    @POST("user/password")
    Observable<ChangedPasswordEntity> changedPassword(@Field("oldpassword") String oldPassword,
                                                      @Field("newpassword") String newPassword);

    @FormUrlEncoded
    @POST("user/guestbook")
    Observable<ChangedPasswordEntity> commitGusetBook(@Field("contents") String oldPassword);


//    @FormUrlEncoded
//    @POST("user/info")
//    Observable<EnergyUsedEntity> getEnergyUsed(@Field("time") String date, @Field("type") String type);

    @FormUrlEncoded
    @POST("scene/add")
    Observable<SampleResult> addScene(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("scene/delete")
    Observable<SampleResult> deleteScene(@Field("id") String id);

    @FormUrlEncoded
    @POST("scene/update")
    Observable<SampleResult> updateScene(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("scene/addchannel")
    Observable<AddSceneChannelResult> addChannel(@Field("scene_id") String scene_id,
                                                 @Field("channel_id") String channel_id,
                                                 @Field("cmd") String cmd);

    @FormUrlEncoded
    @POST("scene/deletechannel")
    Observable<SampleResult> deleteChannel(@Field("id") String scene_id);

    @FormUrlEncoded
    @POST("scene/setchannel")
    Observable<SampleResult> setChannel(@Field("id") String id, @Field("cmd") String cmd);

    @FormUrlEncoded
    @POST("house/add")
    Observable<SampleResult> addFloor(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("house/add_area")
    Observable<SampleResult> addArea(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("house/delete_area")
    Observable<SampleResult> deleteArea(@Field("id") String id);

    @FormUrlEncoded
    @POST("house/delete_channel")
    Observable<SampleResult> deleteAreaChannel(@Field("id") String id);

    @FormUrlEncoded
    @POST("scene/edit")
    Observable<SceneEdit> getSceneInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("energy/more")
    Observable<AmmeterInfos> getAmmeterInfo(@Field("sno") String sno);


    @POST("house/freshchannel")
    Observable<FreshChannelEntity> getFreshChannel();

    @FormUrlEncoded
    @POST("user/profile")
    Observable<SampleResult> updateProfile(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("device/updatechannel")
    Observable<SampleResult> updateChannel(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("energy/ammeter3")
    Observable<Ameter3Entity> getAmmeter3Data(@Field("sno") String sno, @Field("date") String date);

    @FormUrlEncoded
    @POST("house/add_channel")
    Observable<AddSceneChannelResult> addAreaChannel(@Field("area_id") String area_id,
                                                     @Field("channel_id") String channel_id,
                                                     @Field("show_sort") String show_sort);

    @Multipart
    @POST("upload/images")
    Observable<UpLoadImageMessage> upload(
            @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("device/abnormal")
    Observable<YichangEntity> getYichang(@Field("page") String page);

    @FormUrlEncoded
    @POST("house/delete")
    Observable<SampleResult> deleteFloor(@Field("id") String id);

    @FormUrlEncoded
    @POST("device/abnormal_delete")
    Observable<SampleResult> deleteLog(@Field("log_id") String log_id);

    @FormUrlEncoded
    @POST("scene/updatetimer")
    Observable<RespondBody> setSceneTiming(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("scene/gettimer")
    Observable<RespondBody<List<SceneTimeResult>>> getSceneTiming(@Field("scene_id") String scene_id);

    @FormUrlEncoded
    @POST("scene/deletetimer")
    Observable<RespondBody> deleteTimmer(@Field("id") String id);

    @FormUrlEncoded
    @POST("scene/addtimer")
    Observable<RespondBody> addTimmer(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("device/channel_list")
    Observable<DeviceDetailResult> getDeviceDetail(@Field("dev_id") String dev_id);

    @FormUrlEncoded
    @POST("device/channel_edit")
    Observable<ChannelEditorInfo> getChannelEditor(@Field("channel_id") String channel_id);


    /**
     * 查询三相电表实时历史数据
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("energy/history")
    Observable<HisEntity> getZHCurHis(@FieldMap Map<String, String> map);

}
