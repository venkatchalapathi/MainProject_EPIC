package com.venkat.exampletask;

import android.widget.TextView;

import com.app.feng.fixtablelayout.inter.IDataAdapter;

import java.util.List;

public class FixTableAdapter implements IDataAdapter {

    public String[] titles;


    public List<Sheet1> data;

    public FixTableAdapter(String[] titles,List<Sheet1> data) {
        this.titles = titles;
        this.data = data;
    }

    public void setData(List<Sheet1> data) {
        this.data = data;
    }

    @Override
    public String getTitleAt(int pos) {
        return titles[pos];
    }

    @Override
    public int getTitleCount() {
        return titles.length;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void convertData(int position,List<TextView> bindViews) {
        Sheet1 dataBean = data.get(position);

        /*bindViews.get(0)
                .setText(dataBean.getName());
        bindViews.get(1)
                .setText(dataBean.getEPICNo());
        bindViews.get(2)
                .setText(dataBean.getFatherName());
        bindViews.get(3)
                .setText(dataBean.getAddress());
        bindViews.get(4)
                .setText(dataBean.getBooth());
        bindViews.get(5)
                .setText(dataBean.getGender());
        bindViews.get(6)
                .setText(dataBean.getAge());
*/
    }

    @Override
    public void convertLeftData(int position,TextView bindView) {
        bindView.setText(data.get(position).getName());
    }
}
