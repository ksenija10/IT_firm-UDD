import { SearchResponse } from './search-response.model';

export class SearchPage {
  constructor(
    public searchResults: SearchResponse[],
    public totalResults: number
  ) {}
}
