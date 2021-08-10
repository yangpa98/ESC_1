package com.example.esc_1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private Context context;
    private ArrayList<Contact> datalist;

    public Adapter(Context context, ArrayList<Contact> datalist){
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recy_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        Contact contact = datalist.get(position);
        holder.name.setText(contact.getName());
        holder.phonenum.setText(contact.getPhoneNumber());

        holder.call.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("전화를 걸지, 문자를 보낼지 선택해 주십시오.");
                builder.setMessage("골라!!!");
                builder.setNeutralButton("전화걸기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String number = datalist.get(position).getPhoneNumber();
                        Uri numberU= Uri.parse("tel:" + number);
                        Intent call = new Intent(Intent.ACTION_CALL, numberU);
                        context.startActivity(call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });
                builder.setPositiveButton("문자 보내기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri smsUri = Uri.parse("tell:"+ datalist.get(position).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                        intent.putExtra("address", contact.getPhoneNumber());
                        intent.putExtra("sms_body", "");
                        intent.setType("vnd,android-dir/mms-sms");
                        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });
                builder.create().show();
            }
        });

        holder.item_container.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("삭제");
                builder.setMessage("정말 이 연락처를 삭제하시겠습니까?");
                builder.setNegativeButton("아니오", null);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO : 핸드폰에서 연락처 지우기.
                        datalist.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, datalist.size());
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {

        protected ConstraintLayout item_container;
        protected TextView name;
        protected TextView phonenum;
        protected Button call;

        public Holder(@NonNull View itemView) {
            super(itemView);

            item_container = itemView.findViewById(R.id.item_container);
            name = itemView.findViewById(R.id.name);
            phonenum = itemView.findViewById(R.id.phonenum);
            call = itemView.findViewById(R.id.call);
        }
    }
}
