import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Board = () => import('@/entities/board/board.vue');
const BoardUpdate = () => import('@/entities/board/board-update.vue');
const BoardDetails = () => import('@/entities/board/board-details.vue');

const Post = () => import('@/entities/post/post.vue');
const PostUpdate = () => import('@/entities/post/post-update.vue');
const PostDetails = () => import('@/entities/post/post-details.vue');

const AttachGroup = () => import('@/entities/attach-group/attach-group.vue');
const AttachGroupUpdate = () => import('@/entities/attach-group/attach-group-update.vue');
const AttachGroupDetails = () => import('@/entities/attach-group/attach-group-details.vue');

const Attach = () => import('@/entities/attach/attach.vue');
const AttachUpdate = () => import('@/entities/attach/attach-update.vue');
const AttachDetails = () => import('@/entities/attach/attach-details.vue');

const Comment = () => import('@/entities/comment/comment.vue');
const CommentUpdate = () => import('@/entities/comment/comment-update.vue');
const CommentDetails = () => import('@/entities/comment/comment-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'board',
      name: 'Board',
      component: Board,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'board/new',
      name: 'BoardCreate',
      component: BoardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'board/:boardId/edit',
      name: 'BoardEdit',
      component: BoardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'board/:boardId/view',
      name: 'BoardView',
      component: BoardDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post',
      name: 'Post',
      component: Post,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post/new',
      name: 'PostCreate',
      component: PostUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post/:postId/edit',
      name: 'PostEdit',
      component: PostUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post/:postId/view',
      name: 'PostView',
      component: PostDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attach-group',
      name: 'AttachGroup',
      component: AttachGroup,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attach-group/new',
      name: 'AttachGroupCreate',
      component: AttachGroupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attach-group/:attachGroupId/edit',
      name: 'AttachGroupEdit',
      component: AttachGroupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attach-group/:attachGroupId/view',
      name: 'AttachGroupView',
      component: AttachGroupDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attach',
      name: 'Attach',
      component: Attach,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attach/new',
      name: 'AttachCreate',
      component: AttachUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attach/:attachId/edit',
      name: 'AttachEdit',
      component: AttachUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attach/:attachId/view',
      name: 'AttachView',
      component: AttachDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'comment',
      name: 'Comment',
      component: Comment,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'comment/new',
      name: 'CommentCreate',
      component: CommentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'comment/:commentId/edit',
      name: 'CommentEdit',
      component: CommentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'comment/:commentId/view',
      name: 'CommentView',
      component: CommentDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
