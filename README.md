# L'angolo nerd Android App
Android App for [L'angolo nerd](http://www.langolonerd.it)    
Basically it's a WebView used to show mobile version of the site with some useful features.   
    
The development follows the `git-flow branching pattern`, using `develop` as the _bleeding edge_ (and _more unstable_) branch and `master` as the _release branch_.    
Each feature will be developed on an indipendent `feature/feature_name` branch and, once finished, merged into the `develop` one.    

The app is developed using [Android Studio](https://developer.android.com/studio/index.html) and is under [GPL-3.0 License](https://www.gnu.org/licenses/gpl-3.0.en.html).


## Important Notice about Gradle Build
In order to deploy automatically a `signed` release I had to modify Gradle settings.    
In `app/build.gradle` I added the `signingConfigs` section, that refers to variables like `MyApp.signing` etc.    
Those vars needs to be declared in `/gradle.properties` with personal datas like this:

	MyApp.signing=RelativeOrAbsolutePathToKeystore
	MyApp.signing.password=yourPassword
	MyApp.signing.alias=aliasNameOfYourKeystore

So, once cloned this repo you will have two options:
- Remove `signingConfigs` sections from `app/build.gradle`
- Create and populate `gradle.properties` as above
