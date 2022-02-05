import { FormFieldRequest } from './form-field-request.model';

export class AdvancedSearchRequest {
  constructor(public formFieldRequestList: FormFieldRequest[]) {}
}
