import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { ToastrService } from 'ngx-toastr';
import { SearchPage } from 'src/app/model/search-page.model';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-simple-search-form',
  templateUrl: './simple-search-form.component.html',
  styleUrls: ['./simple-search-form.component.css'],
})
export class SimpleSearchFormComponent implements OnInit {
  searchPage: SearchPage = { totalResults: 0, searchResults: [] };
  searchForm: FormGroup;
  pageEvent: PageEvent = new PageEvent();

  waiting = false;

  constructor(
    private searchService: SearchService,
    private toastr: ToastrService
  ) {
    this.searchForm = new FormGroup({
      query: new FormControl('', [Validators.required]),
    });
    this.pageEvent.pageIndex = 0;
    this.pageEvent.pageSize = 5;
  }

  ngOnInit(): void {}

  search() {
    if (!this.searchForm.valid) {
      return;
    }
    this.waiting = true;
    const query = this.searchForm.value.query;
    this.searchService
      .simpleSearch(query, this.pageEvent.pageIndex, this.pageEvent.pageSize)
      .subscribe((response) => {
        this.searchPage = response;
        this.waiting = false;
        if (response.searchResults.length === 0) {
          this.toastr.info(
            'No job applications found matching the search criteria'
          );
        }
      });
  }

  pageChanged(event: PageEvent) {
    this.pageEvent = event;
    this.search();
  }
}
