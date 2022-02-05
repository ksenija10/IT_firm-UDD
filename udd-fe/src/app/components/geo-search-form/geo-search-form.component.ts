import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { ToastrService } from 'ngx-toastr';
import { GeoLocationRequest } from 'src/app/model/geo-search-request.model';
import { SearchPage } from 'src/app/model/search-page.model';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-geo-search-form',
  templateUrl: './geo-search-form.component.html',
  styleUrls: ['./geo-search-form.component.css'],
})
export class GeoSearchFormComponent implements OnInit {
  searchPage: SearchPage = { totalResults: 0, searchResults: [] };
  searchForm: FormGroup;
  pageEvent: PageEvent = new PageEvent();

  waiting = false;

  constructor(
    private searchService: SearchService,
    private toastr: ToastrService
  ) {
    this.searchForm = new FormGroup({
      city: new FormControl('', [Validators.required]),
      radius: new FormControl('', [Validators.required]),
      measureUnit: new FormControl('', [Validators.required]),
    });
    this.pageEvent.pageIndex = 0;
    this.pageEvent.pageSize = 10;
  }

  ngOnInit(): void {}

  search() {
    if (!this.searchForm.valid) {
      return;
    }

    const geoSearchRequest = new GeoLocationRequest(
      this.searchForm.value.city,
      this.searchForm.value.radius,
      this.searchForm.value.measureUnit
    );

    this.waiting = true;

    this.searchService
      .geoLocationSearch(
        geoSearchRequest,
        this.pageEvent.pageIndex,
        this.pageEvent.pageSize
      )
      .subscribe(
        (response) => {
          this.searchPage = response;
          this.waiting = false;
          if (response.searchResults.length === 0) {
            this.toastr.info(
              'No job applications found matching the search criteria'
            );
          }
        },
        (error) => {
          this.waiting = false;
          this.toastr.warning('Entered city name is not valid');
        }
      );
  }

  pageChanged(event: PageEvent) {
    this.pageEvent = event;
    this.search();
  }
}
