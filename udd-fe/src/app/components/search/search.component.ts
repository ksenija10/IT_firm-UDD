import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  selected = new FormControl(0);

  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(): void {}

  logout(): void {
    if (this.selected.value == 3) {
      this.authenticationService.logout();
    }
  }
}
