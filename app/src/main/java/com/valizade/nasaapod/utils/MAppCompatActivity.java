package com.valizade.nasaapod.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

@SuppressLint("Registered")
public class MAppCompatActivity extends AppCompatActivity {

  /**
   * Initializing collapsing toolbar
   * Will show and hide the toolbar title on scroll
   */
  public void initCollapsingToolbar(final String toolbarTitle, int colapsingToolbar, int appBar) {
    final CollapsingToolbarLayout collapsingToolbar = findViewById(colapsingToolbar);
    collapsingToolbar.setTitle(" ");
    AppBarLayout appBarLayout = findViewById(appBar);
    appBarLayout.setExpanded(true);

    // hiding & showing the title when toolbar expanded & collapsed
    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      boolean isShow = false;
      int scrollRange = -1;

      @Override
      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
          scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
          collapsingToolbar.setTitle(toolbarTitle);
          isShow = true;
        } else if (isShow) {
          collapsingToolbar.setTitle(" ");
          isShow = false;
        }
      }
    });
  }

  /**
   * RecyclerView item decoration - give equal margin around grid item
   */
  public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
      this.spanCount = spanCount;
      this.spacing = spacing;
      this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
      int position = parent.getChildAdapterPosition(view); // item position
      int column = position % spanCount; // item column

      if (includeEdge) {
        outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
        outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

        if (position < spanCount) { // top edge
          outRect.top = spacing;
        }
        outRect.bottom = spacing; // item bottom
      } else {
        outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
        outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        if (position >= spanCount) {
          outRect.top = spacing; // item top
        }
      }
    }
  }

  /**
   * Converting dp to pixel
   */
  public int dpToPx(int dp) {
    Resources r = getResources();
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
  }

  public boolean isConnectedToInternet(){
    ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null)
    {
      NetworkInfo[] info = connectivity.getAllNetworkInfo();
      if (info != null)
        for (NetworkInfo anInfo : info)
          if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
            return true;
          }

    }
    return false;
  }

  //simple alert dialog
  public void showAlertDialog(String title, String message, String btnPositive) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(title)
      .setMessage(message)
      .setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          dialog.cancel();
          exit();
        }
      })
      .show();
  }

  //exit of application
  public void exit() {
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    System.exit(1);
  }

}