import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from '../guards/role-guard.service';
import { JobApplicationComponent } from '../components/job-application/job-application.component';
import { SearchComponent } from '../components/search/search.component';
import { LoginComponent } from '../components/login/login.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full',
  },
  { path: 'login', component: LoginComponent },
  { path: 'job-application', component: JobApplicationComponent },
  {
    path: 'search',
    component: SearchComponent,
    canActivate: [RoleGuard],
  },
  { path: '**', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
