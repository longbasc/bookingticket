package vn.com.basc.bookingticket;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.com.basc.bookingticket.common.CommonActivity;
import vn.com.basc.bookingticket.model.Ticket;

public class TicketListActivity extends CommonActivity {

    private VideoItemAdapter mListAdapter;
    private ArrayList<Ticket>  mTicketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        setCommonUI();


        ListView listView = (ListView) this.findViewById(R.id.listview_ticket_list_page);

        mTicketList = new ArrayList<Ticket>();

        int count = 0;
        Ticket ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);

        ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);

        ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);

        ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);

        ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);


        ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);

        ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);

        ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);

        ticket = new Ticket();
        ticket.mTicketID = "Ticket " + (++count);
        ticket.mIsCheck = false;
        mTicketList.add(ticket);





        Ticket[] ItemArr =  new  Ticket[mTicketList.size()];
        mTicketList.toArray(ItemArr);
        mListAdapter  = new VideoItemAdapter(TicketListActivity.this, R.layout.row_ticket, ItemArr);
        listView.setAdapter(mListAdapter);
        //mListAdapter.notifyDataSetChanged();


    }




    //==================================================================================
    public class VideoItemAdapter extends ArrayAdapter<Ticket> {

        Context context;
        int layoutResourceId;
        Ticket data[] = null;

        public VideoItemAdapter(Context context, int layoutResourceId, Ticket[] data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            TicketItemHolder holder = null;
            Ticket item = data[position];

            if(row == null)
            {
                LayoutInflater inflater = ((CommonActivity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new TicketItemHolder();
                holder.item = item;

                holder.bt_submit_row_ticket = (Button) row.findViewById(R.id.bt_submit_row_ticket);
                holder.lb_ticketid_row_ticket = (TextView) row.findViewById(R.id.lb_ticketid_row_ticket);
                holder.ckb_select_row_ticket = (CheckBox) row.findViewById(R.id.ckb_select_row_ticket);


                /*
                holder.ckb_select_row_ticket.setTag(holder);
                holder.ckb_select_row_ticket.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        TicketItemHolder holder = (TicketItemHolder) v.getTag();
                        holder.item.mIsCheck = holder.ckb_select_row_ticket.isChecked();

                    }

                });
                */





                View.OnClickListener clickListener =  new View.OnClickListener() {
                    public void onClick(View v) {

                        TicketItemHolder holder = (TicketItemHolder) v.getTag();
                        //show dialog
                        /*
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.custom);
                        dialog.setTitle("Title...");

                        // set the custom dialog components - text, image and button
                        TextView text = (TextView) dialog.findViewById(R.id.text);
                        text.setText("Android custom dialog example!");
                        ImageView image = (ImageView) dialog.findViewById(R.id.image);
                        image.setImageResource(R.drawable.ic_launcher);

                        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                         */

                    }
                };

                holder.lb_ticketid_row_ticket.setTag(holder);
                holder.lb_ticketid_row_ticket.setOnClickListener(clickListener);

                holder.bt_submit_row_ticket.setTag(holder);
                holder.bt_submit_row_ticket.setOnClickListener(clickListener);

                row.setTag(holder);
            }
            else
            {
                holder = (TicketItemHolder)row.getTag();
                holder.item = item;
            }

            holder.lb_ticketid_row_ticket.setText(holder.item.mTicketID);
            holder.ckb_select_row_ticket.setChecked(holder.item.mIsCheck);

            /*
            if(!holder.item.ImageURL.isEmpty()){
                holder.img_row_image_text_select.setBackgroundResource(0);
                Picasso.with(context).load(holder.item.ImageURL).into(holder.img_row_image_text_select);
            }*/


            return row;
        }

        public  class TicketItemHolder
        {
            Button bt_submit_row_ticket;
            TextView lb_ticketid_row_ticket;
            CheckBox ckb_select_row_ticket;
            Ticket item;
        }
    }


}
