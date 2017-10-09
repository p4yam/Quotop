package ir.kivee.quotop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.kivee.quotop.utils.CapturePhotoUtils;
import ir.kivee.quotop.utils.ColorsFragment;
import ir.kivee.quotop.utils.FontSizeFragment;

public class QuoteActivity extends AppCompatActivity implements View.OnTouchListener {

    private float dX, dY;
    private ImageView fontColor;
    private ImageView backColor;
    private ImageView fontSize;
    private ImageView fontStyle;
    private FrameLayout bottomFrame;
    private ImageView imgCopyText;
    private ImageView imgSaveImage;


    private static RelativeLayout rlImageContainer;
    private static TextView txtQuote;
    private static TextView txtAuthor;
    private static QuoteActivity activity;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        activity = this;
        initViews();

        final ArrayList<String> quoteInfo = new ArrayList<>(getIntent()
                .getStringArrayListExtra("info"));

        txtQuote.setText("“" + quoteInfo.get(1) + "”");

        if (quoteInfo.get(3) == null)
            txtAuthor.setText("-" + quoteInfo.get(2));
        else
            txtAuthor.setText("-" + quoteInfo.get(2) + ", " + quoteInfo.get(3));


        txtQuote.setOnTouchListener(this);
        txtQuote.performClick();
        txtAuthor.setOnTouchListener(this);
        txtAuthor.performClick();

        fontStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChangeFontStyle();
            }
        });

        fontColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChangeFontColor();
            }
        });

        backColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChangeBackColor();
            }
        });

        fontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChangeFontSize();
            }
        });

        imgSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomFrame.setVisibility(View.GONE);
                rlImageContainer.setDrawingCacheEnabled(true);
                Bitmap imageToSave = Bitmap.createBitmap(rlImageContainer.getDrawingCache());
                rlImageContainer.setDrawingCacheEnabled(false);
                String res = CapturePhotoUtils.insertImage(getContentResolver(), imageToSave,
                        "QuoTop", "image created by quotop app");
                if (res != null)
                    Toast.makeText(getApplication(), "Image saved to gallery",
                            Toast.LENGTH_SHORT)
                            .show();
            }
        });

        imgCopyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullQuote = quoteInfo.get(1) + " -" + quoteInfo.get(2);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("*/*");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, fullQuote);
                startActivity(Intent.createChooser(sharingIntent, "select app ..."));
            }
        });

    }

    private void initViews() {
        txtQuote = findViewById(R.id.activity_quote_quote);
        txtAuthor = findViewById(R.id.activity_quote_author);
        fontColor = findViewById(R.id.font_color);
        bottomFrame = findViewById(R.id.activity_quote_frame);
        backColor = findViewById(R.id.back_image);
        fontSize = findViewById(R.id.font_size);
        fontStyle = findViewById(R.id.font_style);
        rlImageContainer = findViewById(R.id.activity_quote_quote_container);
        imgCopyText = findViewById(R.id.activity_quote_copy_text);
        imgSaveImage = findViewById(R.id.activity_quote_save_image);

        Typeface typeQuote = Typeface.createFromAsset
                (getAssets(), "fonts/biko.otf");
        Typeface typeAuthor = Typeface.createFromAsset
                (getAssets(), "fonts/geosans.ttf");

        txtQuote.setTypeface(typeQuote);
        txtAuthor.setTypeface(typeAuthor);
    }

    private void btnChangeFontStyle() {
        if (bottomFrame.getVisibility() == View.GONE) {
            ColorsFragment.getAdapterCase(1);
            ColorsFragment fragment = new ColorsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.activity_quote_frame, fragment).commit();
            bottomFrame.setVisibility(View.VISIBLE);
        } else if (bottomFrame.getVisibility() == View.VISIBLE)
            bottomFrame.setVisibility(View.GONE);
    }

    private void btnChangeFontColor() {
        if (bottomFrame.getVisibility() == View.GONE) {
            ColorsFragment.getAdapterCase(2);
            ColorsFragment fragment = new ColorsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.activity_quote_frame, fragment).commit();
            bottomFrame.setVisibility(View.VISIBLE);
        } else if (bottomFrame.getVisibility() == View.VISIBLE)
            bottomFrame.setVisibility(View.GONE);
    }

    private void btnChangeBackColor() {
        if (bottomFrame.getVisibility() == View.GONE) {
            ColorsFragment.getAdapterCase(3);
            ColorsFragment fragment = new ColorsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.activity_quote_frame, fragment).commit();
            bottomFrame.setVisibility(View.VISIBLE);
        } else if (bottomFrame.getVisibility() == View.VISIBLE)
            bottomFrame.setVisibility(View.GONE);
    }

    private void btnChangeFontSize() {
        if (bottomFrame.getVisibility() == View.GONE) {
            FontSizeFragment fragment = new FontSizeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.activity_quote_frame, fragment).commit();

            bottomFrame.setVisibility(View.VISIBLE);
        } else if (bottomFrame.getVisibility() == View.VISIBLE)
            bottomFrame.setVisibility(View.GONE);
    }

    public static void changeQuoteFont(String style) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), style);
        txtQuote.setTypeface(typeface);
    }

    public static void changeQuoteFontColor(int resId) {
        txtQuote.setTextColor(activity.getResources().getColor(resId));
        txtAuthor.setTextColor(activity.getResources().getColor(resId));

    }

    public static void changeQuoteBackground(int resId) {
        if (resId == 0) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, "Choose an Image")
                    , PICK_IMAGE_REQUEST);
        } else
            rlImageContainer.setBackground(activity.getResources().getDrawable(resId));

    }

    public static void changeQuoteFontSize(float size) {
        txtQuote.setTextSize(size);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                view.performClick();
                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();

                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST &&
                resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap sourceImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Drawable drawable = new BitmapDrawable(getResources(), sourceImage);
                rlImageContainer.setBackground(drawable);
            } catch (Exception e) {
                Log.d("WTF", e.getMessage());
            }
        }

    }
}
