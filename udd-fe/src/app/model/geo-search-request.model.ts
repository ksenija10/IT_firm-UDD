export class GeoLocationRequest {
  constructor(
    public city: string,
    public radius: number,
    public measureUnit: string
  ) {}
}
