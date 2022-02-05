import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { ToastrService } from 'ngx-toastr';
import { AdvancedSearchRequest } from 'src/app/model/advanced-search-request.model';
import { FormFieldRequest } from 'src/app/model/form-field-request.model';
import { SearchPage } from 'src/app/model/search-page.model';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-advanced-search-form',
  templateUrl: './advanced-search-form.component.html',
  styleUrls: ['./advanced-search-form.component.css'],
})
export class AdvancedSearchFormComponent implements OnInit {
  searchPage: SearchPage = { totalResults: 0, searchResults: [] };
  searchForm: FormGroup;
  pageEvent: PageEvent = new PageEvent();

  waiting = false;

  constructor(
    private searchService: SearchService,
    private toastr: ToastrService
  ) {
    this.searchForm = new FormGroup({
      name: new FormControl(''),
      namePhrase: new FormControl(''),
      nameOperator: new FormControl(''),
      surname: new FormControl(''),
      surnamePhrase: new FormControl(''),
      surnameOperator: new FormControl(''),
      educationLevel: new FormControl(''),
      educationLevelPhrase: new FormControl(true),
      educationLevelOperator: new FormControl(''),
      content: new FormControl(''),
      contentPhrase: new FormControl(''),
      contentOperator: new FormControl(''),
    });
    this.pageEvent.pageIndex = 0;
    this.pageEvent.pageSize = 10;
  }

  ngOnInit(): void {}

  search() {
    const advancedSearchRequest = this.createRequest();
    this.waiting = true;
    this.searchService
      .advancedSearch(
        advancedSearchRequest,
        this.pageEvent.pageIndex,
        this.pageEvent.pageSize
      )
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

  createRequest(): AdvancedSearchRequest {
    let formFieldRequestList = [];
    if (this.searchForm.value.name !== '') {
      formFieldRequestList.push(
        new FormFieldRequest(
          'name',
          this.searchForm.value.name,
          this.searchForm.value.nameOperator,
          this.searchForm.value.namePhrase !== ''
        )
      );
    }
    if (this.searchForm.value.surname !== '') {
      formFieldRequestList.push(
        new FormFieldRequest(
          'surname',
          this.searchForm.value.surname,
          this.searchForm.value.surnameOperator,
          this.searchForm.value.surnamePhrase !== ''
        )
      );
    }
    if (this.searchForm.value.educationLevel !== '') {
      formFieldRequestList.push(
        new FormFieldRequest(
          'educationLevel',
          this.searchForm.value.educationLevel,
          this.searchForm.value.educationLevelOperator,
          this.searchForm.value.educationLevelPhrase !== ''
        )
      );
    }
    if (this.searchForm.value.content !== '') {
      formFieldRequestList.push(
        new FormFieldRequest(
          'content',
          this.searchForm.value.content,
          this.searchForm.value.contentOperator,
          this.searchForm.value.contentPhrase !== ''
        )
      );
    }
    return new AdvancedSearchRequest(formFieldRequestList);
  }
}
