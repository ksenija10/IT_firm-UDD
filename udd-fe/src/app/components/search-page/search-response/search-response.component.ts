import { Component, Input, OnInit } from '@angular/core';
import { SearchResponse } from 'src/app/model/search-response.model';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-search-response',
  templateUrl: './search-response.component.html',
  styleUrls: ['./search-response.component.css'],
})
export class SearchResponseComponent implements OnInit {
  @Input() searchResponse: SearchResponse = {} as SearchResponse;
  opened: boolean = false;
  constructor(private searchService: SearchService) {}

  ngOnInit(): void {}

  openCV() {
    this.opened = true;
    this.searchService.openCv(this.searchResponse.cvId);
  }
}
