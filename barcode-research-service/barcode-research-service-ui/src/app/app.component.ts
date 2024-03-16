import {Component, inject} from '@angular/core';
import { GfInverseService } from './gf-inverse.service';
import {RouterOutlet} from '@angular/router';
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  fieldValue: number = 2;
  field: number = 5;
  inverse: number = 3;

  constructor(private gfInverseService: GfInverseService, private formBuilder: FormBuilder) { }

  findInverse() {
    this.gfInverseService.getInverse(this.fieldValue, this.field).subscribe({
      next: value => this.inverse = value,
      error: err => console.error('Error while calculating inverse: ' + err)
    });

  }

  onFieldValueChange(event: Event) {
    this.fieldValue = Number.parseInt((event.target as HTMLInputElement).value);
  }

  onFieldChange(event: Event) {
    this.field = Number.parseInt((event.target as HTMLInputElement).value);
  }
}
