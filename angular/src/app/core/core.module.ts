import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { ThemeService } from './services/theme/theme.service';
import { ClientService } from './services/client/client.service';
import {RouterLink} from "@angular/router";
import {PageNotFoundComponent} from "@core/pages/page-not-found/page-not-found.component";
import {OverlayPanelModule} from "primeng/overlaypanel";
import {ThemeSwitcherComponent} from "@core/components/theme-switcher/theme-switcher.component";
import {MenuModule} from "primeng/menu";
import {DialogModule} from "primeng/dialog";
import {FileUploadModule} from "primeng/fileupload";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [PageNotFoundComponent, ThemeSwitcherComponent],
  exports: [PageNotFoundComponent, ThemeSwitcherComponent],
  providers: [ThemeService, ClientService],
  imports: [CommonModule, RouterLink, NgOptimizedImage, OverlayPanelModule, MenuModule, DialogModule, FileUploadModule, FormsModule],
})
export class CoreModule {}
