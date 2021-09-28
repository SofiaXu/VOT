package site.aoba.android.vot.common.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SmoothSliderAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragments;

    public SmoothSliderAdapter(Fragment parent, List<Fragment> fragments) {
        super(parent);
        this.fragments = fragments;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
