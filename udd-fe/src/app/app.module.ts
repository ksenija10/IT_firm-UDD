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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GeoSearchFormComponent } from './components/geo-search-form/geo-search-form.component';

@NgModule({
  declarations: [
    AppComponent,
    SimpleSearchFormComponent,
    AdvancedSearchFormComponent,
    SearchComponent,
    GeoSearchFormComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonToggleModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
