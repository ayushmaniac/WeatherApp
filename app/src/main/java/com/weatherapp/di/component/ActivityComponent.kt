package com.weatherapp.di.component

import com.weatherapp.di.modules.ActivityModule
import com.weatherapp.di.scopes.ActivityScope
import com.weatherapp.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {




    fun inject(activity : MainActivity)


}