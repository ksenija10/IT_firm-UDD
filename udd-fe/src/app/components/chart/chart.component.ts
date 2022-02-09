import { Component, Input, OnInit } from '@angular/core';
import { ChartResponse } from 'src/app/model/chart.model';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css'],
})
export class ChartComponent implements OnInit {
  @Input() type: string = '';
  @Input() chart: ChartResponse[] = [];
  legendTitle: string = 'Form access by ';

  constructor() {}

  ngOnInit(): void {
    this.legendTitle = this.legendTitle + this.type;
  }
}
