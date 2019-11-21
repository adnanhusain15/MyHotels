package com.example.myhotels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhotels.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.myhotels.Adapters.Rec_HotelsAdapter;
import com.example.myhotels.Adapters.SliderAdapter;
import com.example.myhotels.Decoration.GridSpacingItemDecoration;
import com.example.myhotels.Util.Bookings;
import com.example.myhotels.Util.CurrentUser;
import com.example.myhotels.Util.Drafts;
import com.example.myhotels.Util.Hotel;
import com.example.myhotels.Util.HotelView;
import com.example.myhotels.Util.Reader;
import com.example.myhotels.Util.Writer;

import static com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils.dpToPx;

public class HotelViewer extends AppCompatActivity {
    public String hotelName ;
    public List<Hotel> hotels;
    public Hotel hotel = null;
    SliderView sliderView;
    private List<HotelView> hotelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Rec_HotelsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_viewer);
        Intent intent = getIntent();
        hotelName = intent.getStringExtra("hotelname");
        sliderView = findViewById(R.id.imageSlider);


        //Initializing Image Slider
        final SliderAdapter adapter = new SliderAdapter(this);
        adapter.setCount(2);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.DRAG_FLAG_GLOBAL);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });
        setValues(hotelName,this);

        //Initializing Recommendation RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rec_recycler_view);
        mAdapter = new Rec_HotelsAdapter(getApplicationContext(),hotelList);
//        RecyclerView.LayoutManager mLayoutManager =
//                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareHotelData();
    }


    private Bookings getBooked(){
        /* returns booked hotel for the user*/
        ArrayList<Bookings> bookings = Reader.getBookingsList(getApplicationContext());
        if(bookings == null)
            return null;
        for(Bookings book : bookings){
            String name = book.getName();
            if(name == null) continue;

            if(name.equals(CurrentUser.username))
            {

                return book;

            }
        }

        return null;
    }
    public void prepareHotelData(){
        /*PREPARE HOTEL LIST FOR RECOMMENDING TO USER BASED ON ITS PREVIOUS BOOKINGS*/
        int[] cover = {R.drawable.hicon1,
                R.drawable.hicon2,
                R.drawable.hicon3,
                R.drawable.hicon4};
        TextView title = findViewById(R.id.View_title);
        TextView feats = findViewById(R.id.View_features);
        String[] feat_text = feats.getText().toString().split(": ");
        String[] curr_feats = feat_text[1].split(" , ");

        TextView locs = findViewById(R.id.View_location);
        String[] locs_text = locs.getText().toString().split(": ");
        Random random = new Random();
        Bookings booking = getBooked();
        HotelView hotelview ;
        List<Hotel> hotels = Reader.getRestaurantList(getApplicationContext());

        if(booking==null)
        {
            for(Hotel h : hotels) {
                int idx = random.nextInt(12);
                idx = idx%4;
                if(h.getLocation().equalsIgnoreCase(locs_text[1]) && !h.getName().equalsIgnoreCase(title.getText().toString())) {
                    hotelview = new HotelView(h.getName(), h.getLocation(), cover[idx], h.getRating(), h.getFeats());
                    hotelList.add(hotelview);
                }
            }
        } else {
            List<Integer> ids = booking.getId();
            for(Hotel h : hotels) {
                int idx = random.nextInt(12);
                idx = idx%4;

                if(!ids.contains(h.getId())) {
                    if((h.getFeatures().contains(curr_feats[0]) || h.getFeatures().contains(curr_feats[1]))
                            && (!h.getName().equalsIgnoreCase(title.getText().toString()))) {
                        hotelview = new HotelView(h.getName(), h.getLocation(), cover[idx], h.getRating(), h.getFeats());
                        hotelList.add(hotelview);
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setValues(String hotelName, Context context){
        /*SETTING VALUES TO THE LAYOUT COMPONENTS*/
        TextView viewtitle , viewlocation , viewrating , viewfeature , viewcontact;
        final Button viewbook,viewsave;
        viewtitle = findViewById(R.id.View_title);
        viewlocation = findViewById(R.id.View_location);
        viewrating = findViewById(R.id.View_rating);
        viewfeature = findViewById(R.id.View_features);
        viewcontact = findViewById(R.id.View_contact);
        viewbook = findViewById(R.id.View_book);
        viewsave = findViewById(R.id.View_save);
        hotels = Reader.getRestaurantList(context);

        for(Hotel hot : hotels) {
            if(hot.getName().equalsIgnoreCase(hotelName)) {
                hotel = hot;
                break;
            }
        }
        viewtitle.setText(hotel.getName());
        viewlocation.setText("Locations: "+ hotel.getLocation());
        viewfeature.setText("Features: "+hotel.getFeats());
        viewrating.setText("User Rating: "+hotel.getRating());
        viewcontact.setText("Contact: "+hotel.getContact());




        // ON CLICK LISTNER FOR BOOKIING BUTTON
        viewbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stat = viewbook.getText().toString();
                if(stat.equalsIgnoreCase("book"))
                {
                    viewbook.setText("Booked");
                    int index = -1;
                    ArrayList<Bookings> bookings = Reader.getBookingsList(getApplicationContext());
                    Bookings firstBooking = null;
                    if(bookings == null) //CHECK IF NO BOOKING HAS BEEN DONE BY ANY USER
                    {
                        firstBooking = new Bookings();
                        firstBooking.setName(CurrentUser.username);
                        List<Integer> ids = new ArrayList<>();
                        ids.add(hotel.getId());
                        firstBooking.setId(ids);
                        bookings = new ArrayList<>();
                        bookings.add(firstBooking);
                        Writer.writeBookings(getApplicationContext(), bookings);
                    }
                    else {
                        boolean status = false;
                        for (Bookings book : bookings) {
                            //FINDING ALL THE BOOKINGS FOR THE CURRENTUSER
                            if(book.getName() == null) continue;
                            if (book.getName().equalsIgnoreCase(CurrentUser.username)) {
                                status = true;
                                firstBooking = book;
                                index = bookings.indexOf(book);

                                break;
                            }
                        }
                        if (status) {
                            //USER ALREADY HAS SOME BOOKING
                            List<Integer> ids = firstBooking.getId();
                            if(ids.contains(hotel.getId()))
                                return;
                            ids.add(hotel.getId());
                            bookings.remove(index);
                            firstBooking.setId(ids);
                            bookings.add(firstBooking);

                        } else {
                            //USER BOOKING FOR FIRST TIME
                            firstBooking = new Bookings();
                            firstBooking.setName(CurrentUser.username);
                            List<Integer> ids = new ArrayList<>();
                            ids.add(hotel.getId());
                            firstBooking.setId(ids);
                            bookings.add(firstBooking);
                        }
                        Writer.writeBookings(getApplicationContext(), bookings);
                    }

                }
                else
                        Toast.makeText(getApplicationContext(),"Already Booked",Toast.LENGTH_LONG).show();
            }
        });
        viewsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stat = viewsave.getText().toString();
                if(stat.equalsIgnoreCase("Save for later"))
                {
                    viewsave.setText("Saved");
                    int index = -1;
                    ArrayList<Drafts> drafts = Reader.getDraftsList(getApplicationContext());
                    Drafts firstDraft = null;
                    if(drafts == null){
                        firstDraft = new Drafts();
                        firstDraft.setName(CurrentUser.username);
                        List<Integer> ids = new ArrayList<>();
                        ids.add(hotel.getId());
                        firstDraft.setId(ids);
                        drafts = new ArrayList<>();
                        drafts.add(firstDraft);
                        Writer.writeDrafts(getApplicationContext(), drafts);
                    }
                    else {
                        boolean status = false;
                        for (Drafts book : drafts) {
                            if (book.getName().equalsIgnoreCase(CurrentUser.username)) {
                                status = true;
                                firstDraft = book;
                                index = drafts.indexOf(book);
                                //Toast.makeText(getApplicationContext(),book.getId().size(),Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        if (status) {
                            List<Integer> ids = firstDraft.getId();
                            if(ids.contains(hotel.getId()))
                                return;
                            ids.add(hotel.getId());
                            drafts.remove(index);
                            firstDraft.setId(ids);
                            drafts.add(firstDraft);

                        } else {
                            firstDraft = new Drafts();
                            firstDraft.setName(CurrentUser.username);
                            List<Integer> ids = new ArrayList<>();
                            ids.add(hotel.getId());
                            firstDraft.setId(ids);
                            drafts.add(firstDraft);
                        }
                        Writer.writeDrafts(getApplicationContext(), drafts);
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"Already Saved",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hotel, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_bookings:
                Bookings booking = getBooked();
                List<Hotel> hotels = Reader.getRestaurantList(getApplicationContext());
                if(booking==null)
                    Toast.makeText(getApplicationContext(),"No Bookings for you",Toast.LENGTH_LONG).show();
                else {
                    List<Integer> ids = booking.getId();
                    String res = "";
                    for(Hotel h : hotels) {

                        if(ids.contains(h.getId())) {
                            res = res + h.getName() +"\n";
                        }
                    }
                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}