import { Routes } from '@angular/router';
import {GfpInverseComponent} from "./components/gfp-inverse/gfp-inverse.component";
import {PolynomOperationsComponent} from "./components/polynom-operations/polynom-operations.component";
import {OtherOperationsComponent} from "./components/other-operations/other-operations.component";

export const routes: Routes = [
  {path: "", redirectTo: "gfOperations", pathMatch: "full"},
  {path: "gfOperations", component: GfpInverseComponent},
  {path: "polynomOperations", component: PolynomOperationsComponent},
  {path: "otherOperations", component: OtherOperationsComponent}
];
