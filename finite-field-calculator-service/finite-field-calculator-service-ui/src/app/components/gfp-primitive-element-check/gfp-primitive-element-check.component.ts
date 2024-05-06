import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'gfp-check-primitive',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-primitive-element-check.component.html',
  styleUrl: './gfp-primitive-element-check.component.css'
})
export class GfpPrimitiveElementCheckComponent {
  fieldElement: number = 2;
  field: number = 5;
  result: string = "Елемент є примітивним";

  constructor(private gfOperationsService: GfOperationsService) { }

  checkPrimitive() {
    this.gfOperationsService.checkGfpElementPrimitivity(this.field, this.fieldElement).subscribe({
      next: value => {
        this.result = value.isPrimitive ? "Елемент є примітивним" : "Елемент не є примітивним"
      }
    });
  }
}

