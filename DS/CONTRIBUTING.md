Label WIP = "Don't merge it yet, I just created a PR to see if it builds! ;)+

Normally its 1 PR per JIRA, with normally 1 commit.  You CAN amend a PR if build failed and you're WIP, but should squash when you remove WIP.

The JIRA(s) referenced in the commit message must be Status: In Progress.

Don't use Git revert commits, but squash or skip them before you submit a PR (see http://wiki.oams.com/display/DEVWIKI/GitRevertCommitOnPR).

Watch out for line ending problems (see http://wiki.oams.com/display/DEVWIKI/GitLineEnding).

http://wiki.oams.com/display/DEVWIKI/DSGitPullRequestRejectReasons may be has more.
