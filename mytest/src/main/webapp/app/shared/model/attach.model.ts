import { type IAttachGroup } from '@/shared/model/attach-group.model';

export interface IAttach {
  id?: number;
  ord?: number | null;
  name?: string | null;
  origName?: string | null;
  ext?: string | null;
  contentType?: string | null;
  path?: string | null;
  fileSize?: number | null;
  createdAt?: Date;
  createdBy?: number;
  modifiedAt?: Date | null;
  modifiedBy?: number | null;
  attachGroup?: IAttachGroup | null;
}

export class Attach implements IAttach {
  constructor(
    public id?: number,
    public ord?: number | null,
    public name?: string | null,
    public origName?: string | null,
    public ext?: string | null,
    public contentType?: string | null,
    public path?: string | null,
    public fileSize?: number | null,
    public createdAt?: Date,
    public createdBy?: number,
    public modifiedAt?: Date | null,
    public modifiedBy?: number | null,
    public attachGroup?: IAttachGroup | null,
  ) {}
}
