import settings from '@/settings'

const title = settings.title || 'Vue Admin Template'

export function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}

export function setPageTitle(pageTitle) {
  document.title = getPageTitle(pageTitle)
}
