import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { SimpleSearchFormComponent } from './components/simple-search-form/simple-search-form.component';
import { AdvancedSearchFormComponent } from './components/advanced-search-form/advanced-search-form.component';
import { SearchComponent } from './components/search/search.component';

import { MatTabsModule } from '@angular/material/tabs';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GeoSearchFormComponent } from './components/geo-search-form/geo-search-form.component';
import { SearchPageComponent } from './components/search-page/search-page.component';
import { SearchResponseComponent } from './components/search-page/search-response/search-response.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { JobApplicationComponent } from './components/job-application/job-application.component';
import { AuthInterceptorService } from './interceptors/auth-interceptor.service';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { LoginComponent } from './components/login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    SimpleSearchFormComponent,
    AdvancedSearchFormComponent,
    SearchComponent,
    GeoSearchFormComponent,
    SearchPageComponent,
    SearchResponseComponent,
    JobApplicationComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([]),
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatTabsModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonToggleModule,
    MatPaginatorModule,
    ToastrModule.forRoot(),
    MatTooltipModule,
    AppRoutingModule,
    MatProgressSpinnerModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      // ako multi nije true ovo bi bio jedini interceptor i pregazio bi sve defaultne interceptore
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
