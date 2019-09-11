package com.mindyourlovedone.healthcare.DropBox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.SharedFileMetadata;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for file list
 */
public class SharedFilesAdapter extends RecyclerView.Adapter<SharedFilesAdapter.MetadataViewHolder> {
    private final Picasso mPicasso;
    private final Callback mCallback;
    private List<DropBoxFileItem> mFiles;

    public SharedFilesAdapter(Picasso picasso, Callback callback) {
        mPicasso = picasso;
        mCallback = callback;
    }


    public void setSharedFiles(ArrayList<DropBoxFileItem> resultList) {
        mFiles = Collections.unmodifiableList(new ArrayList<>(resultList));
        notifyDataSetChanged();
    }

    @Override
    public MetadataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.files_item, viewGroup, false);
        return new MetadataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MetadataViewHolder metadataViewHolder, int i) {
        metadataViewHolder.bind(mFiles.get(i));
    }

    @Override
    public long getItemId(int position) {
        if (mFiles.get(position).getShared() == 1) {
            return mFiles.get(position).getSharefmd().getPathLower().hashCode();
        } else {
            return mFiles.get(position).getFilemd().getPathLower().hashCode();
        }
    }

    @Override
    public int getItemCount() {
        return mFiles == null ? 0 : mFiles.size();
    }


    public interface Callback {
        void onFolderClicked(DropBoxFileItem folder);

        void onFileClicked(DropBoxFileItem file);

        void onFolderClicked(FolderMetadata folder);
    }

    public class MetadataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTextView, txttype;
        private final ImageView mImageView;
        private DropBoxFileItem mItem;

        public MetadataViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mTextView = itemView.findViewById(R.id.text);
            txttype = itemView.findViewById(R.id.txttype);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItem instanceof DropBoxFileItem) {
                if (mItem.getShared() == 1) {
                    mCallback.onFileClicked(mItem);
                } else {
                    Metadata mm = mItem.getFilemd();

                    if (mm instanceof FolderMetadata) {
                        mCallback.onFolderClicked((FolderMetadata) mm);
                    } else if (mm instanceof FileMetadata) {
                        mCallback.onFileClicked(mItem);
                    }
                }
            }
        }

        public void bind(DropBoxFileItem item) {
            mItem = item;
            if (item.getShared() == 1) {
                SharedFileMetadata ss = item.getSharefmd();
                mTextView.setText(ss.getName());
                txttype.setText("Shared File");
                if (ss instanceof SharedFileMetadata) {
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = ss.getName().substring(ss.getName().indexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);
                    if (type != null && type.startsWith("image/")) {
                        mPicasso.load(FileThumbnailRequestHandler.buildPicassoUri((SharedFileMetadata) ss))
                                .placeholder(R.drawable.ic_photo_grey_600_36dp)
                                .error(R.drawable.ic_photo_grey_600_36dp)
                                .into(mImageView);
                    } else {
                        mPicasso.load(R.drawable.ic_insert_drive_file_blue_36dp)
                                .noFade()
                                .into(mImageView);
                    }
                } else if (item instanceof DropBoxFileItem) {
                    mPicasso.load(R.drawable.ic_folder_blue_36dp)
                            .noFade()
                            .into(mImageView);
                }
            } else {
                Metadata ff = item.getFilemd();
                mTextView.setText(ff.getName());
                if (ff instanceof FileMetadata) {
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = ff.getName().substring(ff.getName().indexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);
                    if (type != null && type.startsWith("image/")) {
                        mPicasso.load(FileThumbnailRequestHandler.buildPicassoUri((FileMetadata) ff))
                                .placeholder(R.drawable.ic_photo_grey_600_36dp)
                                .error(R.drawable.ic_photo_grey_600_36dp)
                                .into(mImageView);
                    } else {
                        mPicasso.load(R.drawable.ic_insert_drive_file_blue_36dp)
                                .noFade()
                                .into(mImageView);
                    }
                } else if (item instanceof DropBoxFileItem) {
                    mPicasso.load(R.drawable.ic_folder_blue_36dp)
                            .noFade()
                            .into(mImageView);
                }
            }


            // Load based on file path
            // Prepending a magic scheme to get it to
            // be picked up by DropboxPicassoRequestHandler


        }
    }
}
