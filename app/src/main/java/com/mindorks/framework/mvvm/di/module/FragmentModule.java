package com.mindorks.framework.mvvm.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/*
 * Author: rotbolt
 */

@Module
public class FragmentModule {

    private BaseFragment<?, ?> fragment;
    private com.mindorks.framework.mvvm.ui.base2.BaseFragment<?, ?> fragment2;

    public FragmentModule(BaseFragment<?, ?> fragment) {
        this.fragment = fragment;
    }

    public FragmentModule(com.mindorks.framework.mvvm.ui.base2.BaseFragment<?, ?> fragment) {
        this.fragment2 = fragment;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(fragment.getActivity());
    }

//    @Provides
//    AboutViewModel provideAboutViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
//        Supplier<AboutViewModel> supplier = () -> new AboutViewModel(dataManager, schedulerProvider);
//        ViewModelProviderFactory<AboutViewModel> factory = new ViewModelProviderFactory<>(AboutViewModel.class, supplier);
//        return new ViewModelProvider(fragment, factory).get(AboutViewModel.class);
//    }
//
//    @Provides
//    BlogAdapter provideBlogAdapter() {
//        return new BlogAdapter(new ArrayList<>());
//    }
//
//
//    @Provides
//    BlogViewModel provideBlogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
//        Supplier<BlogViewModel> supplier = () -> new BlogViewModel(dataManager, schedulerProvider);
//        ViewModelProviderFactory<BlogViewModel> factory = new ViewModelProviderFactory<>(BlogViewModel.class, supplier);
//        return new ViewModelProvider(fragment, factory).get(BlogViewModel.class);
//    }
//
//    @Provides
//    OpenSourceAdapter provideOpenSourceAdapter() {
//        return new OpenSourceAdapter();
//    }
//
//    @Provides
//    OpenSourceViewModel provideOpenSourceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
//        Supplier<OpenSourceViewModel> supplier = () -> new OpenSourceViewModel(dataManager, schedulerProvider);
//        ViewModelProviderFactory<OpenSourceViewModel> factory = new ViewModelProviderFactory<>(OpenSourceViewModel.class, supplier);
//        return new ViewModelProvider(fragment, factory).get(OpenSourceViewModel.class);
//    }
}
