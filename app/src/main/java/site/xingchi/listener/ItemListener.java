package site.xingchi.listener;

import android.view.View;

import site.xingchi.pojo.Contacts;

public interface ItemListener {
    void onItemClick(int position, Contacts contacts);
    void onItemLongClick(int position, Contacts contacts, View view);
}
