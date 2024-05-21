import { defineComponent, provide } from 'vue';

import BoardService from './board/board.service';
import PostService from './post/post.service';
import AttachGroupService from './attach-group/attach-group.service';
import AttachService from './attach/attach.service';
import CommentService from './comment/comment.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('boardService', () => new BoardService());
    provide('postService', () => new PostService());
    provide('attachGroupService', () => new AttachGroupService());
    provide('attachService', () => new AttachService());
    provide('commentService', () => new CommentService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
