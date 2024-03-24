import { Component } from '@angular/core';
import {GfpMulPolyComponent} from "../gpf-mul-poly/gfp-mul-poly.component";

@Component({
  selector: 'app-polynom-operations',
  standalone: true,
  imports: [
    GfpMulPolyComponent
  ],
  templateUrl: './polynom-operations.component.html',
  styleUrl: './polynom-operations.component.css'
})
export class PolynomOperationsComponent {

}
