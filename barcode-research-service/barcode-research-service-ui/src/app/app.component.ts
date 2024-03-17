import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {GfpInverseComponent} from "./components/gfp-inverse/gfp-inverse.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, GfpInverseComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

}
