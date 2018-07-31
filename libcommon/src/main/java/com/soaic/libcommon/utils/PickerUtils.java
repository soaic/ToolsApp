package com.soaic.libcommon.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soaic.libcommon.R;
import com.soaic.libcommon.model.AddressModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 选择器工具类
 * Created by Soaic on 2017/10/25.
 */
public class PickerUtils {

    private static ArrayList<AddressModel> provinces;
    private static ArrayList<AddressModel> firstList;
    private static ArrayList<List<AddressModel>> secondList;
    private static ArrayList<List<List<AddressModel>>> thirdList;

    private final static int TITLE_COLOR = Color.parseColor("#333333");
    private final static int CANCEL_COLOR = Color.parseColor("#999999");
    private final static int SUBMIT_COLOR = Color.parseColor("#32ACC3");
    private final static int DIVIDER_COLOR = Color.parseColor("#CCCCCC");
    private final static int TEXT_COLOR = Color.parseColor("#333333");

    /**
     * 显示单个选择器
     */
    public static void showOptionPicker(final Activity activity, final List<String> list, final OnOptionPickerListener callback){
        if(list == null || list.size()<=0){
            return;
        }
        OptionsPickerView<String> pickerView = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                callback.onOptionPicked(options1,list.get(options1));
            }
        }).setTitleText("")
            .setCancelText(activity.getString(R.string.picker_cancel))
            .setSubmitText(activity.getString(R.string.picker_submit))
            .setSubCalSize(16)
            .setContentTextSize(16)//设置滚轮文字大小
            .setSelectOptions(0)//默认选中项
            .setLineSpacingMultiplier(2.0f)////设置两横线之间的间隔倍数
            .setBgColor(Color.WHITE)
            .setTitleBgColor(Color.WHITE)
            .setTitleColor(TITLE_COLOR)
            .setCancelColor(CANCEL_COLOR)
            .setSubmitColor(SUBMIT_COLOR)
            .setDividerColor(DIVIDER_COLOR)//设置分割线的颜色
            .setTextColorCenter(TEXT_COLOR)
            .setBackgroundId(0x66000000)
            .build();
        pickerView.setPicker(list);
        pickerView.show();
    }

    /**
     * 显示单个选择器
     */
    public static void showOptionPicker(Activity activity, String[] list, final OnOptionPickerListener callback){
        if(list == null || list.length <= 0){
            return;
        }
        showOptionPicker(activity, new ArrayList<>(Arrays.asList(list)), callback);
    }

    /**
     * 显示地址选择器
     */
    public static void showAddressPicker(Activity activity, final OnAddressPickerListener callback) {
        try {
            initAddressData(activity);
            OptionsPickerView<AddressModel> pickerView = new  OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    callback.onAddressPicked(getSelectedProvince(options1),getSelectedCity(options1,option2),getSelectedCounty(options1,option2,options3));
                }
            }).setTitleText("")
                    .setCancelText(activity.getString(R.string.picker_cancel))
                    .setSubmitText(activity.getString(R.string.picker_submit))
                    .setSubCalSize(16)
                    .setContentTextSize(16)//设置滚轮文字大小
                    .setSelectOptions(0)//默认选中项
                    .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                    .setBgColor(Color.WHITE)
                    .setTitleBgColor(Color.WHITE)
                    .setTitleColor(TITLE_COLOR)
                    .setCancelColor(CANCEL_COLOR)
                    .setSubmitColor(SUBMIT_COLOR)
                    .setDividerColor(DIVIDER_COLOR)//设置分割线的颜色
                    .setTextColorCenter(TEXT_COLOR)
                    .setBackgroundId(0x66000000)
                    .build();
            pickerView.setPicker(firstList,secondList,thirdList);
            pickerView.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示双向选择器
     */
    public static void showDoublePicker(Activity activity, List<String> firstData, List<String> secondData, final OnDoublePickerListener callback){
        OptionsPickerView<String> pickerView = new  OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                callback.onDoublePicked(options1, option2);
            }
        }).setTitleText("")
                .setCancelText(activity.getString(R.string.picker_cancel))
                .setSubmitText(activity.getString(R.string.picker_submit))
                .setSubCalSize(16)
                .setContentTextSize(16) //设置滚轮文字大小
                .setSelectOptions(0) //默认选中项
                .setLineSpacingMultiplier(2.0f) //设置两横线之间的间隔倍数
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(TITLE_COLOR)
                .setCancelColor(CANCEL_COLOR)
                .setSubmitColor(SUBMIT_COLOR)
                .setDividerColor(DIVIDER_COLOR) //设置分割线的颜色
                .setTextColorCenter(TEXT_COLOR)
                .setBackgroundId(0x66000000)
                .build();
        pickerView.setNPicker(firstData, secondData,null);
        pickerView.show();
    }

    /**
     * 单个选择
     */
    public static void showSingleSelect(Activity activity, final List<String> ids, List<String> names, final TextView view){
        showOptionPicker(activity, names, new OnOptionPickerListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                view.setText(option); //在文本框中显示选择的选项
                view.setTag(ids.get(position));
            }
        });
    }

    /**
     * 时间选择年月日
     */
    public static void showTimeYMDPicker(Activity activity, String title, Calendar selectDate , Calendar startDate, Calendar endDate, final OnTimePickerListener listener){
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if(listener != null) listener.onTimePicked(date);
            }
        }).setTitleText("")
                .setCancelText(activity.getString(R.string.picker_cancel))
                .setTitleText(title)
                .setSubmitText(activity.getString(R.string.picker_submit))
                .setSubCalSize(16)
                .setLineSpacingMultiplier(2.5f)//设置两横线之间的间隔倍数
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(TITLE_COLOR)
                .setCancelColor(CANCEL_COLOR)
                .setSubmitColor(SUBMIT_COLOR)
                .setDividerColor(DIVIDER_COLOR)//设置分割线的颜色
                .setTextColorCenter(TEXT_COLOR)
                .setLabel("","","","","","")//默认设置为年月日时分秒
                .setBackgroundId(0x66000000)
                .setRangDate(startDate,endDate)
                .setType(new boolean[]{ true, true, true, false, false, false }).build();
        pvTime.setDate(selectDate);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }


    public static void showCustomTimePicker(Activity activity, final OnTimePickerListener listener){
        TimePickerView pvTime = new TimePickerBuilder(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if(listener != null) listener.onTimePicked(date);
            }
        }).setLayoutRes(0, new CustomListener() {
            @Override
            public void customLayout(View v) {

            }
        }).build();
    }


    private static void initAddressData(Activity activity) throws IOException {
        String json = ConvertUtils.inputStreamToStr(activity.getAssets().open("city.json"));
        Type type = new TypeToken<ArrayList<AddressModel>>() {}.getType();
        if(provinces!=null&&provinces.size()>0){
            return;
        }
        provinces = (new Gson().fromJson(json,type));
        firstList = new ArrayList<>();
        secondList = new ArrayList<>();
        thirdList = new ArrayList<>();
        int provinceSize = provinces.size();
        //添加省
        for (int x = 0; x < provinceSize; x++) {
            AddressModel pro = provinces.get(x);
            firstList.add(pro);
            List<AddressModel> cities = pro.getPlaces();
            List<AddressModel> xCities = new ArrayList<>();
            List<List<AddressModel>> xCounties = new ArrayList<>();
            int citySize = cities.size();
            //添加地市
            for (int y = 0; y < citySize; y++) {
                AddressModel cit = cities.get(y);
                xCities.add(cit);
                List<AddressModel> counties = cit.getPlaces();
                ArrayList<AddressModel> yCounties = new ArrayList<>();
                int countySize = counties.size();
                //添加区县
                for (int z = 0; z < countySize; z++) {
                    AddressModel cou = counties.get(z);
                    yCounties.add(cou);
                }
                xCounties.add(yCounties);
            }
            secondList.add(xCities);
            thirdList.add(xCounties);
        }
    }

    private static AddressModel getSelectedProvince(int selectedFirstIndex) {
        return provinces.get(selectedFirstIndex);
    }

    private static AddressModel getSelectedCity(int selectedFirstIndex, int selectedSecondIndex) {
        List<AddressModel> cities = getSelectedProvince(selectedFirstIndex).getPlaces();
        if (cities.size() == 0) {
            return null;//可能没有第二级数据
        }
        return cities.get(selectedSecondIndex);
    }

    private static AddressModel getSelectedCounty(int selectedFirstIndex, int selectedSecondIndex, int selectedThirdIndex) {
        AddressModel AddressModel = getSelectedCity(selectedFirstIndex, selectedSecondIndex);
        if(AddressModel!=null){
            List<AddressModel> counties = AddressModel.getPlaces();
            if (counties.size() == 0) {
                return null;//可能没有第三级数据
            }
            return counties.get(selectedThirdIndex);
        }
        return null;
    }

    public interface OnTimePickerListener{
        void onTimePicked(Date date);
    }

    public interface OnAddressPickerListener{
        void onAddressPicked(AddressModel province, AddressModel city, AddressModel county);
    }

    public interface OnDoublePickerListener{
        void onDoublePicked(int selectedFirstIndex, int selectedSecondIndex);
    }

    public interface OnOptionPickerListener{
        void onOptionPicked(int position, String option);
    }
}
