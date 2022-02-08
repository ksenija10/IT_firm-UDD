import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { JobApplicationService } from 'src/app/services/job-application.service';

@Component({
  selector: 'app-job-application',
  templateUrl: './job-application.component.html',
  styleUrls: ['./job-application.component.css'],
})
export class JobApplicationComponent implements OnInit {
  jobApplicationForm: FormGroup;
  cv: any = null;
  cvType: string = '';
  cvName: string = '';
  constructor(
    private jobApplicationService: JobApplicationService,
    private toastr: ToastrService
  ) {
    this.jobApplicationForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      surname: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      address: new FormControl('', [Validators.required]),
      educationLevel: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
    let latitude: number;
    let longitude: number;
    navigator.geolocation.getCurrentPosition((position) => {
      latitude = position.coords.latitude;
      longitude = position.coords.longitude;
      console.log(latitude);
      console.log(longitude);
    });
  }

  apply() {
    if (!this.jobApplicationForm.valid || this.cvType !== 'pdf') {
      return;
    }

    let jobApplicationRequest: FormData = new FormData();
    jobApplicationRequest.append('name', this.jobApplicationForm.value.name);
    jobApplicationRequest.append(
      'surname',
      this.jobApplicationForm.value.surname
    );
    jobApplicationRequest.append('email', this.jobApplicationForm.value.email);
    jobApplicationRequest.append(
      'address',
      this.jobApplicationForm.value.address
    );
    jobApplicationRequest.append(
      'educationId',
      this.jobApplicationForm.value.educationLevel
    );
    jobApplicationRequest.append('cv', this.cv);

    this.jobApplicationService.applyForJob(jobApplicationRequest).subscribe(
      (response) => {
        this.toastr.success('Your job application has been submited');
      },
      (error) => {
        this.toastr.error('Entered address is not valid');
      }
    );
  }

  showCvName(event: any) {
    this.cv = event.target.files[0];
    this.cvName = this.cv.name;
    this.cvType = this.cvName.substring(this.cvName.lastIndexOf('.') + 1);
  }
}
