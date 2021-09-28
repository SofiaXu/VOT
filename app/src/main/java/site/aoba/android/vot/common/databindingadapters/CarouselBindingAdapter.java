package site.aoba.android.vot.common.databindingadapters;

import android.os.Handler;
import android.os.Looper;
import androidx.core.view.ViewCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableList;
import androidx.viewpager2.widget.ViewPager2;
import dagger.hilt.android.scopes.FragmentScoped;
import site.aoba.android.vot.R;
import site.aoba.android.vot.common.adapters.CarouselAdapter;
import site.aoba.android.vot.common.adapters.VideoListAdapter;
import site.aoba.android.vot.common.architecture.NavigableViewModel;
import site.aoba.android.vot.models.Video;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

public class CarouselBindingAdapter {
    @BindingAdapter({ "carouselItems", "carouselParent"})
    public static void setEnableCarouse(ViewPager2 viewPager2, ObservableList<Video> videos, NavigableViewModel<?> viewModel) {
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        CarouselAdapter c = new CarouselAdapter(viewModel);
        c.setVideos(videos);
        viewPager2.setAdapter(c);
        viewPager2.setOffscreenPageLimit(3);
        float pageMargin= viewPager2.getResources().getDimensionPixelOffset(R.dimen.carousel_page_margin);
        float pageOffset = viewPager2.getResources().getDimensionPixelOffset(R.dimen.carousel_page_offset);
        AtomicInteger itemPosition = new AtomicInteger(0);
        viewPager2.setPageTransformer((page, position) -> {
            float myOffset = position * -(2 * pageOffset + pageMargin);
            if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.setTranslationX(-myOffset);
                } else {
                    page.setTranslationX(myOffset);
                }
            } else {
                page.setTranslationY(myOffset);
            }
        });
        new Handler(Looper.myLooper()).postDelayed(() -> {
            int currentItem = viewPager2.getCurrentItem();
            int size = videos.size();
            if (++currentItem == size)
                currentItem = 0;
            viewPager2.setCurrentItem(currentItem);
        }, 1000);
    }
}