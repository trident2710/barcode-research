import {Routes} from '@angular/router';
import {PolynomOperationsComponent} from "./components/polynom-operations/polynom-operations.component";
import {OtherOperationsComponent} from "./components/other-operations/other-operations.component";
import {GfpOperationsComponent} from "./components/gfp-operations/gfp-operations.component";
import {ReedSolomonOperationsComponent} from "./components/reed-solomon-operations/reed-solomon-operations.component";

export const routes: Routes = [
  {path: "", redirectTo: "gfOperations", pathMatch: "full"},
  {path: "gfOperations", component: GfpOperationsComponent},
  {path: "polynomOperations", component: PolynomOperationsComponent},
  {path: "reedSolomonOperations", component: ReedSolomonOperationsComponent},
  {path: "otherOperations", component: OtherOperationsComponent}
];
