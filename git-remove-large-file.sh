


# The commands below are a guide to remove a large file that has been
# accidentally committed to a Git repository's history. If the file is
# larger than 100 MB, GitHub will prevent you from pushing your latest
# commits. The annotated steps below should help you remove the large
# file from your commit history, even if you've made new commit since.


# Some Git users advise against rebasing. You can safely use it here
# because you haven't published your changes yet.
# So, you first need to rebase your current branch onto the point that
# is published on GitHub. I'm assuming you're on the master branch.
git rebase -i origin/master
# This will open a text editor with a list of commit between origin/master
# and your current commit. For the commit where you've added the large file,
# change "pick" to "edit". Then, save and exit the text editor.


# Git is going to do some stuff and eventually, it'll stop at the commit
# that you labelled with "edit". You now have the opportunity to delete the
# large file that you accidentally committed and amend your commit.
git rm T3AOnline BFME Universal Launcher.jar
# When amending your commit, you can update your message to exclude text
# that refers to the big file.
git commit --amend --allow-empty
# After amending your commit, you can resume the rebasing process.
# If none of the subsequent commits affect the large file that you have
# now removed, everything should go smoothly.
git rebase --continue
# If you did edit the large file in a subsequent commit, you'll run into
# an error. Simply repeat the above three steps.


# Once you're done rebasing your commits and removing the large file
# from the repository's history, you should be able to push your new
# commits to GitHub.
git push origin master


