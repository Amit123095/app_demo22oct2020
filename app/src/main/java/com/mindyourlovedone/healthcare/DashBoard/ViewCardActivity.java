package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mindyourlovedone.healthcare.Connections.RelationActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.HomeActivity.SliderPagerAdapter;
import com.mindyourlovedone.healthcare.database.CardQuery;
import com.mindyourlovedone.healthcare.database.HospitalHealthQuery;
import com.mindyourlovedone.healthcare.model.Card;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.Header;
import com.mindyourlovedone.healthcare.pdfdesign.InsurancePdf;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

public class ViewCardActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    TextView txtNew, txtRegistered, txtProviderValue, txtTypeValue,txtDelete,txtEdit;
    ImageView imageBack,imgHome,imgRight,floatOptions;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    ArrayList<String> slider_text_list = new ArrayList<>();
    int page_position = 0;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    private TextView[] dots;
    Preferences preferences;
    Card card;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        initImageLoader();
        initUI();
        initListener();
        initComponent();
        init();


// method for adding indicators
        addBottomDots(0);

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, false);
            }
        };

       /* new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 2000);*/

    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ins_card)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    private void initComponent() {
        Intent i = getIntent();
        if (i.getExtras() != null) {
             card = (Card) i.getSerializableExtra("CardObject");
            String front = card.getImgFront();
            String back = card.getImgBack();
            String provider = card.getName();
            String type = card.getType();

            txtProviderValue.setText(provider);
            txtTypeValue.setText(type);
            slider_image_list = new ArrayList<>();
            slider_image_list.add(front);
            slider_image_list.add(back);

        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(50);
            dots[i].setTextColor(Color.parseColor("#F0F0F0"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#0DA8D8"));
    }


    private void init() {
        vp_slider = findViewById(R.id.vp_slider);
        ll_dots = findViewById(R.id.ll_dots);
        getImages();
        sliderPagerAdapter = new SliderPagerAdapter(ViewCardActivity.this, slider_image_list, slider_text_list);
        vp_slider.setAdapter(sliderPagerAdapter);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getImages() {

    }

    private void initListener() {
        imgRight.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        txtDelete.setOnClickListener(this);
        txtEdit.setOnClickListener(this);
    }

    private void initUI() {
        preferences = new Preferences(ViewCardActivity.this);
        imageBack = findViewById(R.id.imgBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtTypeValue = findViewById(R.id.txtTypeValue);
        txtProviderValue = findViewById(R.id.txtProviderValue);
        txtTypeValue = findViewById(R.id.txtTypeValue);
        imgRight = findViewById(R.id.imgRight);
        imgHome = findViewById(R.id.imgHome);
        floatOptions = findViewById(R.id.floatOptions);
        txtDelete = findViewById(R.id.txtDelete);
        txtEdit = findViewById(R.id.txtEdit);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
            case R.id.floatOptions:
             //   showFloatPdfDialog();
                break;

            case R.id.imgRight:
                Intent ib = new Intent(ViewCardActivity.this, InstructionActivity.class);
                ib.putExtra("From", "CardInstruction");
                startActivity(ib);
                break;
            case R.id.txtDelete:
                deleteCard(card);
                break;
            case R.id.txtEdit:
                Intent i = new Intent(context, AddCardActivity.class);
                i.putExtra("CardObject", card);
                i.putExtra("IsEdit", true);
                context.startActivity(i);
                finish();
                break;
        }
    }

    public void deleteCard(final Card card) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ViewCardActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = CardQuery.deleteRecord(card.getId());
                if (flag == true) {
                    //     Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();

    }

    private void showFloatPdfDialog() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "InsuranceCard.pdf");
        if (file.exists()) {
            file.delete();
        }

        new Header().createPdfHeader(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
        preferences.copyFile("ic_launcher.png",ViewCardActivity.this);
        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
        Header.addEmptyLine(1);
        Header.addusereNameChank("Insurance Cards");//preferences.getString(PrefConstants.CONNECTED_NAME));
        Header.addEmptyLine(1);
        Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

        Paragraph p = new Paragraph(" ");
        LineSeparator line = new LineSeparator();
        line.setOffset(-4);
        line.setLineColor(BaseColor.LIGHT_GRAY);
        p.add(line);
        try {
            Header.document.add(p);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Header.addEmptyLine(1);
               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Insurance Information");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                // Header.addEmptyLine(2);*/

        ArrayList<Card> CardList = CardQuery.fetchAllCardRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        new InsurancePdf(CardList, 1);

        Header.document.close();

        //----------------------------------
        final Dialog dialog = new Dialog(ViewCardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) ViewCardActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent_pdf, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
//   final ImageView floatCancel = dialogview.findViewById(R.id.floatCancel);  // Rahul
        final FloatingActionButton floatViewPdf = dialogview.findViewById(R.id.floatContact);
        floatViewPdf.setImageResource(R.drawable.eyee);
        final FloatingActionButton floatEmail = dialogview.findViewById(R.id.floatNew);
        floatEmail.setImageResource(R.drawable.closee);

        TextView txtNew = dialogview.findViewById(R.id.txtNew);
        txtNew.setText(getResources().getString(R.string.EmailReports));

        TextView txtContact = dialogview.findViewById(R.id.txtContact);
        txtContact.setText(getResources().getString(R.string.ViewReports));

        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        // int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        rlView.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        floatCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        floatEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/InsuranceCard.pdf";

                File f = new File(path);
                preferences.emailAttachement(f, ViewCardActivity.this, "Insurance Card");
                dialog.dismiss();

            }
        });

        floatViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/InsuranceCard.pdf";
                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getInsuranceCard());


                new PDFDocumentProcess(path,
                        ViewCardActivity.this, result);

                System.out.println("\n" + result + "\n");
                dialog.dismiss();
            }
        });

    }
}
