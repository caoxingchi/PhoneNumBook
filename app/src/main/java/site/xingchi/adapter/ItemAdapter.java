package site.xingchi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import site.xingchi.R;
import site.xingchi.listener.ItemListener;
import site.xingchi.pojo.Contacts;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater ll;
    private Context context;
    private List<Contacts> contactsList;

    private ItemListener itemListener;

    public ItemListener getItemListener() {
        return itemListener;
    }
    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }
    public ItemAdapter(Context context, List<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
        this.ll=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=ll.inflate(R.layout.one_item,viewGroup,false);
        return new ItemViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ItemViewHolder)(viewHolder)).updateContacts();
        final Contacts contacts=contactsList.get(i);
        final int position=i;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onItemClick(position,contacts);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemListener.onItemLongClick(position,contacts,v);
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return contactsList.size();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView phoneNumShow;
        private LinearLayout llBg;
        private ImageView iconCon;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            llBg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
            iconCon = (ImageView) itemView.findViewById(R.id.icon_con);
            name = (TextView) itemView.findViewById(R.id.name);
            phoneNumShow = (TextView) itemView.findViewById(R.id.phoneNumShow);
        }
        public void updateContacts(){
            int position=this.getLayoutPosition();
            if(position%2==0){
                llBg.setBackground(context.getResources().getDrawable(R.drawable.item_bg_t));
                iconCon.setImageResource(R.mipmap.contact_icon);
            }else{
                llBg.setBackground(context.getResources().getDrawable(R.drawable.item_bg));
                iconCon.setImageResource(R.mipmap.contact_icon2);
            }
            Contacts contacts=contactsList.get(position);
            name.setText(contacts.getName());
            phoneNumShow.setText(contacts.getPhone_num());
        }
    }
}
