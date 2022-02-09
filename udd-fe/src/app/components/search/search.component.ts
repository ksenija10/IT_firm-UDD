import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ChartResponse } from 'src/app/model/chart.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ReportService } from 'src/app/services/report.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  selected = new FormControl(0);
  chart: ChartResponse[] = [];

  constructor(
    private authenticationService: AuthenticationService,
    private reportService: ReportService
  ) {}

  ngOnInit(): void {}

  tabChanged(): void {
    if (this.selected.value === 3) {
      // time
      this.reportService
        .getTimeReport()
        .subscribe((response) => (this.chart = response));
    }
    if (this.selected.value === 4) {
      // city
      this.reportService
        .getCityReport()
        .subscribe((response) => (this.chart = response));
    }
    if (this.selected.value === 5) {
      this.authenticationService.logout();
    }
  }
}
