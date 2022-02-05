export class FormFieldRequest {
  constructor(
    public name: string,
    public value: string,
    public operator: string,
    public phrase: boolean
  ) {}
}
