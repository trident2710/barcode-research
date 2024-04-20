import { Component } from '@angular/core';
import {GfpMulPolyComponent} from "../gpf-mul-poly/gfp-mul-poly.component";
import {GfpDivPolyComponent} from "../gfp-div-poly/gfp-div-poly.component";
import {GfpPolyValueComponent} from "../gfp-poly-value/gfp-poly-value.component";

@Component({
  selector: 'app-polynom-operations',
  standalone: true,
  imports: [
    GfpMulPolyComponent,
    GfpDivPolyComponent,
    GfpPolyValueComponent
  ],
  templateUrl: './polynom-operations.component.html',
  styleUrl: './polynom-operations.component.css'
})
export class PolynomOperationsComponent {

}
