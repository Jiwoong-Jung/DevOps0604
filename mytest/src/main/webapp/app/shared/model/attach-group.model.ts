import { type IPost } from '@/shared/model/post.model';

export interface IAttachGroup {
  id?: number;
  createdAt?: Date;
  createdBy?: number;
  post?: IPost | null;
}

export class AttachGroup implements IAttachGroup {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public createdBy?: number,
    public post?: IPost | null,
  ) {}
}
