import { Component, Input, OnInit } from '@angular/core';
import { SearchPage } from 'src/app/model/search-page.model';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css'],
})
export class SearchPageComponent implements OnInit {
  @Input() searchPage: SearchPage = {} as SearchPage;
  constructor() {}

  ngOnInit(): void {}
}
