import { Component } from '@angular/core';
import {GfpMulPolyComponent} from "../gfp-mul-poly/gfp-mul-poly.component";
import {GfpDivPolyComponent} from "../gfp-div-poly/gfp-div-poly.component";
import {GfpPolyValueComponent} from "../gfp-poly-value/gfp-poly-value.component";
import {GfpMulPolyModuloComponent} from "../gfp-mul-poly-modulo/gfp-mul-poly-modulo.component";

@Component({
  selector: 'app-polynom-operations',
  standalone: true,
  imports: [
    GfpMulPolyComponent,
    GfpDivPolyComponent,
    GfpPolyValueComponent,
    GfpMulPolyModuloComponent
  ],
  templateUrl: './polynom-operations.component.html',
  styleUrl: './polynom-operations.component.css'
})
export class PolynomOperationsComponent {

}
