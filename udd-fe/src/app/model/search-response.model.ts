export class SearchResponse {
  constructor(
    public name: string,
    public surname: string,
    public email: string,
    public education: string,
    public address: string,
    public highlight: string,
    public cvId: number
  ) {}
}
