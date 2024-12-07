import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideToastr, ToastrService } from 'ngx-toastr';


bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    ...appConfig.providers || [], // Include any existing providers in `appConfig`
    provideHttpClient(withFetch()), // Add HttpClientModule here
    provideAnimations(), // Replaces BrowserAnimationsModule
    provideToastr({
      progressBar: true,
      closeButton:true,
      newestOnTop:true,
      tapToDismiss:true,
      positionClass:'toast-bottom-right',
      timeOut:8000
    }),
    
     
  ],
})
  .catch((err) => console.error(err));
